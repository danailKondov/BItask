package ru.bellintegrator.practice.registration.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.bellintegrator.practice.exceptionhandler.exceptions.AccountException;
import ru.bellintegrator.practice.registration.service.ComputeHashService;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created on 13.03.2018.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class ComputeHashServiceImpl implements ComputeHashService {

    @Override
    public String getSHA256HashFromString(String source) {
        try {
            MessageDigest encoder = MessageDigest.getInstance("SHA-256");
            byte[] digest = encoder.digest(source.getBytes("UTF-8"));
            return new String(Base64.getEncoder().encode(digest));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new AccountException("Ошибка верификации логина: ошибка кодирования пароля", e);
        }
    }
}
