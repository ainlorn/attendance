package com.midgetspinner31.attendance.enumerable

enum class StatusCode(val code: Int, val httpCode: Int, val message: String?) {
    UNKNOWN(-1, 500, "Неизвестная ошибка"),
    OK(0, 200, null),
    VALIDATION_ERROR(1, 400, "Ошибка валидации"),
    METHOD_NOT_ALLOWED(2, 405, "Метод не разрешён"),
    MISSING_BODY(3, 400, "Отсутствует тело запроса"),
    ACCESS_DENIED(4, 403, "Доступ к ресурсу запрещён"),
    UNAUTHORIZED(5, 401, "Для доступа к этому ресурсу необходимо войти"),
    OPERATION_NOT_ALLOWED(6, 400, "Запрещённая операция"),

    // user account
    LOGIN_IN_USE(100, 400, "Логин уже используется"),
    EMAIL_IN_USE(101, 400, "Адрес электронной почты уже используется"),
    PHONE_IN_USE(102, 400, "Номер телефона уже используется"),
    WRONG_CREDENTIALS(103, 400, "Неправильный логин или пароль"),
    USER_NOT_FOUND(104, 404, "Пользователь не найден"),

    //Group
    GROUP_NOT_FOUND(200, 404, "Группа не найдена"),
    MEMBER_ALREADY_IN_GROUP(201, 400, "Пользователь уже добавлен в группу")
}
