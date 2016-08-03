package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.*;
import gui.AlertBox;
import gui.ConfirmBox;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.DateUtil;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tbaios on 14.07.2016.
 */
public class MainController implements Initializable {

    @FXML
    private TableColumn<OneScedule, MyDate> tableColumnStop;
    @FXML
    private TableColumn<OneScedule, MyDate> tableColumnStart;
    @FXML
    private TableColumn<OneScedule, MyDate> tableColumnDuration;
    @FXML
    private TableView<OneScedule> tableView;
    @FXML
    private Button button;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Label label;
    @FXML
    private Button delete;

    private boolean isRunning;
    private Where where;
    private Gson gson;
    private final File file = new File("saves.json");

    private final ObservableList<OneScedule> scedules = FXCollections.observableArrayList();
    private final ObservableList<String> options = FXCollections.observableArrayList();

    private Task<List<MyDate>> task;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.isRunning = false;
            this.tableColumnStart.setCellValueFactory(new PropertyValueFactory<>("start"));
            this.tableColumnStop.setCellValueFactory(new PropertyValueFactory<>("stop"));
            this.tableColumnDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
            this.tableView.setItems(this.scedules);
            this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(OneScedule.class, new OneSceduleSerializer());
            gsonBuilder.registerTypeAdapter(OneScedule.class, new OneSceduleDeserializer());
            this.gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
            if (!this.file.exists()) {
                this.file.createNewFile();
            }
            BufferedReader br = new BufferedReader(new FileReader(this.file));
            this.where = this.gson.fromJson(br, Where.class);

            //System.out.println(where);
            if (this.where == null) {
                this.where = new Where();
            }
            this.where.getCorperations().forEach((corp -> {
                this.options.add(corp.getName());
            }));
            this.comboBox.setItems(options);
            if (this.comboBox.getItems().size() > 0) {
                this.comboBox.setValue(this.comboBox.getItems().get(0));
                comboBoxAction();
            }
            //System.out.println(DateUtil.roundToQuarter(new MyDate(new GregorianCalendar(1990,7,26,23,59,21))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void buttonClicked() {
        if (this.isRunning) {
            this.isRunning = false;
            this.button.setText("Start");
            this.comboBox.setDisable(false);
            this.delete.setDisable(false);
        } else if (!isRunning && this.comboBox.getValue() != null) {
            this.isRunning = true;
            this.button.setText("Stop");
            this.comboBox.setDisable(true);
            this.delete.setDisable(true);
            if (this.comboBox.getSelectionModel().getSelectedIndex() > -1 && this.comboBox.getValue().equalsIgnoreCase(this.comboBox.getItems().get(this.comboBox.getSelectionModel().getSelectedIndex()))) {
                task = getTask();
            } else if (this.comboBox.getValue() != null) {
                this.where.addCorp(new Corperation(this.comboBox.getValue()));
                this.options.add(this.comboBox.getValue());
                comboBoxAction();
                task = getTask();
            } else {
                return;
            }
            theRunner(this.task, () -> saveIt(this.task.valueProperty()));
            new Thread(this.task).start();
        }
    }

    private static void writeJson(File f, String json) {
        try {
            FileWriter writer;

            writer = new FileWriter(f);

            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void comboBoxAction() {
        //System.out.println(this.where.getCorp(this.comboBox.getSelectionModel().getSelectedItem()));
        if (this.where.getCorp(this.comboBox.getSelectionModel().getSelectedItem()) != null) {
            scedules.clear();
            where.getCorp(comboBox.getSelectionModel().getSelectedItem()).getTimes().forEach(scedules::add);
        }
    }

    private Task<List<MyDate>> getTask() {
        return new Task<List<MyDate>>() {
            @Override
            protected List<MyDate> call() throws Exception {
                long start = System.currentTimeMillis();
                MyDate startDate = new MyDate();
                String seconds;
                String minutes;
                String hours;
                long elapsedTime = 0;
                while (isRunning) {
                    //printMemory();
                    Thread.sleep(500);
                    elapsedTime = System.currentTimeMillis() - start;
                    elapsedTime = elapsedTime / 1000;

                    seconds = Integer.toString((int) (elapsedTime % 60));
                    minutes = Integer.toString((int) ((elapsedTime % 3600) / 60));
                    hours = Integer.toString((int) (elapsedTime / 3600));

                    if (seconds.length() < 2) {
                        seconds = "0" + seconds;
                    }

                    if (minutes.length() < 2) {
                        minutes = "0" + minutes;
                    }

                    if (hours.length() < 2) {
                        hours = "0" + hours;
                    }

                    updateMessage(hours + ":" + minutes + ":" + seconds);
                }
                ArrayList<MyDate> list = new ArrayList<>();
                list.add(DateUtil.roundToQuarterDown(startDate));
                list.add(DateUtil.roundToQuarterUp(new MyDate()));
                return list;
            }
        };
    }

    private void saveIt(ReadOnlyObjectProperty<List<MyDate>> result) {
        result.get();
        if (this.where.getCorp(this.comboBox.getSelectionModel().getSelectedItem()) != null) {
            where.getCorp(comboBox.getSelectionModel().getSelectedItem()).getTimes().add(new OneScedule(result.get().get(0), result.get().get(1)));
            scedules.clear();
            where.getCorp(comboBox.getSelectionModel().getSelectedItem()).getTimes().forEach(scedules::add);
            //System.out.println(gson.toJson(where));
            this.where.getCorp(comboBox.getSelectionModel().getSelectedItem()).getTimes().forEach(e -> System.out.println(e));
        }

        writeJson(this.file, gson.toJson(where));
    }

    public interface InitCompletionHandler {
        void complete();
    }

    private void theRunner(Task<?> task, InitCompletionHandler initCompletionHandler) {
        this.label.textProperty().bind(task.messageProperty());
        task.stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                initCompletionHandler.complete();
            }
        });
    }

    @FXML
    public void deleteButtonClicked() {
        if (tableView.getSelectionModel().getSelectedIndices().size() > 0) {
            if (ConfirmBox.display("Delete", "You realy want delete the Times?")) {
                Corperation corp = this.where.getCorp(this.comboBox.getSelectionModel().getSelectedItem());
                //System.out.println(corp);
                tableView.getSelectionModel().getSelectedIndices().forEach(i -> {
                    List<OneScedule> listC = corp.getTimes().stream().filter(s -> !((s.getStart().getCalendar().getTime() == scedules.get(i).getStart().getCalendar().getTime()) & (s.getStop().getCalendar().getTime() == scedules.get(i).getStop().getCalendar().getTime()))).collect(Collectors.toList());
                    scedules.remove(i);
                    corp.setTimes(listC);
                });
                comboBoxAction();
                writeJson(file,gson.toJson(where));
            }
        } else if (this.comboBox.getSelectionModel().getSelectedIndex() > -1 && !isRunning) {
            if (ConfirmBox.display("Delete", "You realy want delete the Corperation?")) {
                this.where.deleteCorp(this.comboBox.getSelectionModel().getSelectedItem());
                this.options.remove(this.comboBox.getSelectionModel().getSelectedIndex());
                this.scedules.clear();
                if (this.comboBox.getItems().size() > 0) {
                    this.comboBox.getSelectionModel().select(0);
                    comboBoxAction();
                }
                writeJson(file, gson.toJson(where));
            }
        }
    }

    @FXML
    public void calculateButtonClicked(){
        if(this.tableView.getItems().size() > 0){
            double time = 0;
            for(OneScedule sc : this.tableView.getItems()){
                time += sc.getDuration();
            }
            String seconds = Integer.toString((int) (time % 60));
            String minutes = Integer.toString((int) ((time % 3600) / 60));
            String hours = Integer.toString((int) (time / 3600));
            AlertBox.display("Result", hours + ":" + minutes + ":" + seconds);
        }
    }

    public static void printMemory(){
        int mb = 1024*1024;

        //Getting the runtime reference from system
        Runtime runtime = Runtime.getRuntime();

        System.out.println("##### Heap utilization statistics [MB] #####");

        //Print used memory
        System.out.println("Used Memory:"
                + (runtime.totalMemory() - runtime.freeMemory()) / mb);

        //Print free memory
        System.out.println("Free Memory:"
                + runtime.freeMemory() / mb);

        //Print total available memory
        System.out.println("Total Memory:" + runtime.totalMemory() / mb);

        //Print Maximum available memory
        System.out.println("Max Memory:" + runtime.maxMemory() / mb);
    }
}
