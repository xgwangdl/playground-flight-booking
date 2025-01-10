package ai.spring.demo.ai.playground.client;

import ai.spring.demo.ai.playground.javaInterview.InterViewTools;
import ai.spring.demo.ai.playground.javaInterview.InterViewService;
import ai.spring.demo.ai.playground.mail.MailService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import java.util.List;

import ai.spring.demo.ai.playground.services.BookingTools.BookingDetails;
import ai.spring.demo.ai.playground.services.FlightBookingService;

@BrowserCallable
@AnonymousAllowed
public class BookingService {
    private final FlightBookingService flightBookingService;
    private final InterViewService interViewService;
    private final MailService mailService;

    public BookingService(FlightBookingService flightBookingService, InterViewService interViewService, MailService mailService) {
        this.flightBookingService = flightBookingService;
        this.interViewService = interViewService;
        this.mailService = mailService;
    }

    public List<BookingDetails> getBookings() {
        return flightBookingService.getBookings();
    }

    public List<InterViewTools.InterViewRecord> getInterView() {return this.interViewService.getInterViews();}

    public void sendMail(String number,String name) {
        this.mailService.sendMailForAttachment(number,name);
    }
}
