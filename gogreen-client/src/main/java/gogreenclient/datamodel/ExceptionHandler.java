package gogreenclient.datamodel;

import gogreenclient.screens.ScreenConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;


@Service
@Scope("prototype")
public class ExceptionHandler {

    @Autowired
    private ScreenConfiguration screens;

    @Autowired
    private Messenger messenger;


    /**
     * Handler of IllegalArgumentException, which will get the message from the exception,
     * and make the informDialog shown with the message.
     *
     * @param exception an instance of IllegalArgumentException.
     */
    public void illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        messenger.showMessage(exception.getMessage());
    }

    public void internalServerErrorHandler(HttpServerErrorException exception) {
        messenger.showMessage("Server error, please try again");
    }

    public void notFoundHandler(HttpClientErrorException exception, String message) {
        messenger.showMessage(message + " not found. Please try again.");
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }
}
