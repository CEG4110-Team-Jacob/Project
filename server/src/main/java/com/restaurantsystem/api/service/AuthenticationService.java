package com.restaurantsystem.api.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantsystem.api.data.Table;
import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.data.Worker.Job;
import com.restaurantsystem.api.repos.TableRepository;
import com.restaurantsystem.api.repos.WorkerRepository;
import com.restaurantsystem.api.shared.manager.PostCreateAccount;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthenticationService {
    /**
     * How long the token lasts until expiration
     */
    private static final long TOKEN_ACCESS_TIME = 60 * 60 * 24 * 1000; // 24 hours

    // TODO put the key somewhere else
    private static final String SECRET_KEY = "noijgnweiuongqeonhOHIOUHEGIUEhgiuSHeghshoGSEBUIGsG";

    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    private static final JwtParser JWT_PARSER = Jwts.parser().verifyWith(KEY).build();

    /**
     * Generates a token
     * 
     * @param username
     * @return token String
     */
    private static String generateToken(String username) {
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + TOKEN_ACCESS_TIME);
        return Jwts.builder().subject(username).expiration(expirationDate).signWith(KEY).compact();
    }

    private static boolean isValidToken(String token) {
        try {
            Claims claims = JWT_PARSER.parseSignedClaims(token).getPayload();
            if (!claims.getExpiration().after(new Date()))
                return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Autowired
    public WorkerRepository workerRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    TableRepository tableRepository;

    @Transactional
    public Optional<String> login(String username, String password) {
        Optional<Worker> worker = workerRepository.findByUsername(username);
        if (worker.isEmpty())
            return Optional.empty();
        if (!passwordEncoder.matches(password, worker.get().getPasswordHash()))
            return Optional.empty();
        String token = generateToken(username);
        worker.get().setToken(token);
        workerRepository.save(worker.get());
        return Optional.of(token);
    }

    @Transactional
    public Optional<Worker> authenticate(String token) {
        if (!isValidToken(token))
            return Optional.empty();
        return workerRepository.findByToken(token);
    }

    /**
     * If the token is valid and has job. It also automatically accepts manager
     * tokens without checking for jobs.
     * 
     * @param token
     * @param job
     * @return
     */
    public Optional<Worker> hasJobAndAuthenticate(String token, Job job) {
        Optional<Worker> worker = authenticate(token);
        if (worker.isEmpty())
            return Optional.empty();
        if (worker.get().getJob() != job && worker.get().getJob() != Job.Manager)
            return Optional.empty();
        return worker;
    }

    @Transactional
    public void logout(String token) {
        Optional<Worker> worker = authenticate(token);
        if (worker.isEmpty())
            return;
        worker.get().setToken(null);
        workerRepository.save(worker.get());
    }

    @Transactional
    public Optional<Worker> addWorker(PostCreateAccount account) {
        if (workerRepository.existsByUsername(account.username()))
            return Optional.empty();
        if (account.age() < 16)
            return Optional.empty();
        Worker worker = new Worker();
        worker.setAge(account.age());
        worker.setFirstName(account.firstName());
        worker.setLastName(account.lastName());
        worker.setJob(account.job());
        worker.setUsername(account.username());
        Iterable<Table> tables = tableRepository.findAllById(account.tableIds());
        worker.setTables(new HashSet<>());
        tables.forEach(worker.getTables()::add);
        worker.setPasswordHash(passwordEncoder.encode(account.password()));
        worker = workerRepository.save(worker);
        return Optional.of(worker);
    }
}
