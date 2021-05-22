package ie.ucd.dfh.service;

import ie.ucd.dfh.model.ConfirmationToken;

public interface EmailSenderService {
    void sendForgotEmail(String email, ConfirmationToken confirmationToken);
}
