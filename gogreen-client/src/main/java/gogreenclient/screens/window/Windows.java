package gogreenclient.screens.window;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class Windows extends Stage {
    private StageStyle stageStyle;

    public Windows(StageStyle style) {
        super(style);
    }
}
