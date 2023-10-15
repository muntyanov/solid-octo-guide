package ru.tinkoff.edu.seminar.lesson2.exception;

public class NotCreateShortLinkException extends Exception{
    public NotCreateShortLinkException(){
       super("Не удалось создать короткую ссылку. Попробуйте еще раз");
    }
}
