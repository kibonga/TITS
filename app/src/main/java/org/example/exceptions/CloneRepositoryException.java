package org.example.exceptions;

import lombok.Getter;

@Getter
public class CloneRepositoryException extends RuntimeException {

    private final String repositoryName;
    private final String branchName;

    public CloneRepositoryException(String message, String repositoryName,
        String branchName) {
        super(message);
        this.repositoryName = repositoryName;
        this.branchName = branchName;
    }
}
