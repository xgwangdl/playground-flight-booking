package ai.spring.demo.ai.playground.services;

import ai.spring.demo.ai.playground.data.*;
import ai.spring.demo.ai.playground.javaAssistant.InterViewTools;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class InterViewService {

    private final InterViewData db;

    public InterViewService() {
        this.db = new InterViewData();

        initDemoData();
    }

    private void initDemoData() {
        List<String> firstNames = List.of("张三", "李四", "Lucas", "光哥", "Robert","栋哥","皮带哥");

        InterView interView;
        var interViews = new ArrayList<InterView>();

        for (int i = 0; i < firstNames.size(); i++) {
            interView = new InterView();
            interView.setName(firstNames.get(i));
            interView.setNumber("00"+ (i + 1));
            interView.setInterViewStatus(InterViewStatus.WAIT);
            interViews.add(interView);
        }

        // Reset the database on each start
        db.setInterViewList(interViews);
    }

    public List<InterViewTools.InterViewRecord> getInterViews() {
        return db.getInterViewList().stream().map(this::toInterViewDetails).toList();
    }

    private InterView findInterView(String number, String name) {
        return db.getInterViewList().stream()
                .filter(b -> b.getNumber().equalsIgnoreCase(number))
                .filter(b -> b.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Interview not found"));
    }

    public void changeInterView(String number, String name, int score) {
        var interView = findInterView(number, name);
        interView.setScore(score);
        switch (score) {
            case 5:
                interView.setInterViewStatus(InterViewStatus.PERFECT);
                break;
            case 4:
                interView.setInterViewStatus(InterViewStatus.EXCELLENT);
                break;
            case 3:
                interView.setInterViewStatus(InterViewStatus.QUALIFIED);
                break;
            default:
                interView.setInterViewStatus(InterViewStatus.ELIMINATE);
                break;
        }

    }
    public InterViewTools.InterViewRecord getInterViewDetails(String number, String name) {
        var booking = findInterView(number, name);
        return toInterViewDetails(booking);
    }

    private InterViewTools.InterViewRecord toInterViewDetails(InterView interView){
        return new InterViewTools.InterViewRecord(
                interView.getNumber(),
                interView.getName(),
                interView.getScore(),
                interView.getInterViewStatus().toString()
        );
    }
}
