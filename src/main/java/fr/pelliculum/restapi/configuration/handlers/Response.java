package fr.pelliculum.restapi.configuration.handlers;

import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class Response {

    /**
     * Send a response with a message and a status
     * @param message {@link String} message
     * @param status {@link HttpStatus} status
     * @param data {@link Object} response
     * @return {@link ResponseEntity} response
     */
    public static ResponseEntity<Object> send(@Nullable String message, @Nullable HttpStatus status, @Nullable Object data){
        Map<String, Object> map = Map.of(
            "message", message == null ? "" : message,
            "status", status == null ? HttpStatus.OK.value() : status.value(),
            "data", data == null ? Map.of() : data
        );
        return new ResponseEntity<Object>(map, status == null ? HttpStatus.OK : status);
    }

    /**
     * Send a response with a message and a status
     * @param message {@link String} message
     * @param status {@link HttpStatus} status
     * @return {@link ResponseEntity} response
     */
    public static ResponseEntity<Object> send(@Nullable String message, @Nullable HttpStatus status) {
        return send(message, status, null);
    }

    /**
     * Send a response with a message and a OK status
     * @param message {@link String} message
     * @return {@link ResponseEntity} response
     */
    public static ResponseEntity<Object> send(@Nullable String message) {
        return send(message, null, null);
    }

    public static ResponseEntity<Object> ok(@Nullable String message, @Nullable Object data) {
        return send(message, HttpStatus.OK, data);
    }

    public static ResponseEntity<Object> ok(@Nullable String message) {
        return send(message, HttpStatus.OK, null);
    }

    public static ResponseEntity<Object> ok() {
        return send(null, HttpStatus.OK, null);
    }

    public static ResponseEntity<Object> error(@Nullable String message, @Nullable Object data) {
        return send(message, HttpStatus.BAD_REQUEST, data);
    }

    public static ResponseEntity<Object> error(@Nullable String message) {
        return send(message, HttpStatus.BAD_REQUEST, null);
    }

    public static ResponseEntity<Object> error() {
        return send(null, HttpStatus.BAD_REQUEST, null);
    }

    public static ResponseEntity<Object> notFound(@Nullable String message, @Nullable Object data) {
        return send(message, HttpStatus.NOT_FOUND, data);
    }

    public static ResponseEntity<Object> notFound(@Nullable String message) {
        return send(message, HttpStatus.NOT_FOUND, null);
    }

    public static ResponseEntity<Object> notFound() {
        return send(null, HttpStatus.NOT_FOUND, null);
    }

    public static ResponseEntity<Object> unauthorized(@Nullable String message, @Nullable Object data) {
        return send(message, HttpStatus.UNAUTHORIZED, data);
    }

    public static ResponseEntity<Object> unauthorized(@Nullable String message) {
        return send(message, HttpStatus.UNAUTHORIZED, null);
    }

    public static ResponseEntity<Object> unauthorized() {
        return send(null, HttpStatus.UNAUTHORIZED, null);
    }

    public static ResponseEntity<Object> forbidden(@Nullable String message, @Nullable Object data) {
        return send(message, HttpStatus.FORBIDDEN, data);
    }

    public static ResponseEntity<Object> forbidden(@Nullable String message) {
        return send(message, HttpStatus.FORBIDDEN, null);
    }

    public static ResponseEntity<Object> forbidden() {
        return send(null, HttpStatus.FORBIDDEN, null);
    }

    public static ResponseEntity<Object> conflict(@Nullable String message, @Nullable Object data) {
        return send(message, HttpStatus.CONFLICT, data);
    }

    public static ResponseEntity<Object> conflict(@Nullable String message) {
        return send(message, HttpStatus.CONFLICT, null);
    }

    public static ResponseEntity<Object> conflict() {
        return send(null, HttpStatus.CONFLICT, null);
    }

    public static ResponseEntity<Object> internalServerError(@Nullable String message, @Nullable Object data) {
        return send(message, HttpStatus.INTERNAL_SERVER_ERROR, data);
    }

    public static ResponseEntity<Object> internalServerError(@Nullable String message) {
        return send(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    public static ResponseEntity<Object> internalServerError() {
        return send(null, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    public static ResponseEntity<Object> badRequest(@Nullable String message, @Nullable Object data) {
        return send(message, HttpStatus.BAD_REQUEST, data);
    }

    public static ResponseEntity<Object> badRequest(@Nullable String message) {
        return send(message, HttpStatus.BAD_REQUEST, null);
    }

    public static ResponseEntity<Object> badRequest() {
        return send(null, HttpStatus.BAD_REQUEST, null);
    }

    public static ResponseEntity<Object> created(@Nullable String message, @Nullable Object data) {
        return send(message, HttpStatus.CREATED, data);
    }

    public static ResponseEntity<Object> created(@Nullable String message) {
        return send(message, HttpStatus.CREATED, null);
    }

    public static ResponseEntity<Object> created() {
        return send(null, HttpStatus.CREATED, null);
    }


    public static ResponseEntity<Object> accepted(@Nullable String message, @Nullable Object data) {
        return send(message, HttpStatus.ACCEPTED, data);
    }

    public static ResponseEntity<Object> accepted(@Nullable String message) {
        return send(message, HttpStatus.ACCEPTED, null);
    }

    public static ResponseEntity<Object> accepted() {
        return send(null, HttpStatus.ACCEPTED, null);
    }

}
