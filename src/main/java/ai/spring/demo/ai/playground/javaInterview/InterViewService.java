package ai.spring.demo.ai.playground.javaInterview;

import ai.spring.demo.ai.playground.audio.services.AudioService;
import ai.spring.demo.ai.playground.data.*;
import ai.spring.demo.ai.playground.javaInterview.InterViewTools;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InterViewService {

    private final AudioService audioService;
    private final InterViewData db;

    public InterViewService(AudioService audioService) {
        this.db = new InterViewData();

        initDemoData();

        this.audioService = audioService;
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
            interView.setEmail("xgwangdl@163.com");
            interViews.add(interView);
        }

        // Reset the database on each start
        db.setInterViewList(interViews);
    }

    public List<InterViewTools.InterViewRecord> getInterViews() {
        return db.getInterViewList().stream().map(this::toInterViewDetails).toList();
    }

    public InterView findInterView(String number, String name) {
        return db.getInterViewList().stream()
                .filter(b -> b.getNumber().equalsIgnoreCase(number))
                .filter(b -> b.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Interview not found"));
    }

    public void changeInterView(String number, String name, int score, String evaluate) {
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
        interView.setEvaluate(evaluate);
        String mp3Path = this.audioService.getSpeech(number + name,evaluate);
        interView.setMp3Path(mp3Path);
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
                interView.getInterViewStatus().toString(),
                interView.getEvaluate(),
                interView.getEmail(),
                interView.getMp3Path()
        );
    }
}
