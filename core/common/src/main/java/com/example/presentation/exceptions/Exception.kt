package com.example.presentation.exceptions

open class AppException : RuntimeException()

class EmptyFieldException(
    val field: com.example.presentation.constants.Field
) : AppException()

class PasswordMismatchException : AppException()

class AccountAlreadyExistsException : AppException()

class AuthException : AppException()

class StorageException: AppException()
