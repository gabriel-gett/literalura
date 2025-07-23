package com.challenge.literalura.service;

public interface IConvierteDatos {
    <T> T getData(String json, Class<T> tClass);
}
