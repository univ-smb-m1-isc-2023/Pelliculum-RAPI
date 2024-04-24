package fr.pelliculum.restapi.configuration.exceptions;

public class ListNotFoundException extends RuntimeException{

    public ListNotFoundException(String message){
        super(message);
    }

}
