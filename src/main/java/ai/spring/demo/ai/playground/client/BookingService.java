package ai.spring.demo.ai.playground.client;

import ai.spring.demo.ai.playground.data.InterView;
import ai.spring.demo.ai.playground.javaAssistant.InterViewTools;
import ai.spring.demo.ai.playground.services.InterViewService;
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

    public BookingService(FlightBookingService flightBookingService,InterViewService interViewService) {
        this.flightBookingService = flightBookingService;
        this.interViewService = interViewService;
    }

    public List<BookingDetails> getBookings() {
        return flightBookingService.getBookings();
    }

    public List<InterViewTools.InterViewRecord> getInterView() {return this.interViewService.getInterViews();}
}
