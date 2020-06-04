package com.yinglishzhi.tools.ui.forms;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.freedesktop.dbus.interfaces.Local;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

/**
 * @author LDZ
 * @date 2020/6/3 2:13 下午
 */
public class TimeStampWindowUi extends BaseWindowUi implements ActionListener {

    /**
     * 容器
     */
    private JPanel container;

    /**
     * 主面板
     */
    private JPanel mainPanel;

    /**
     * 当前 label
     */
    private JLabel nowLabel;

    /**
     * 控制按钮 用来控制 现在时间自动更新
     */
    private JButton control;

    /**
     * 时间戳 label
     */
    private JLabel timeStamp;

    /**
     * 时间 label
     */
    private JLabel Label3;

    /**
     * 当前时间时间戳 自动更新用
     */
    private JTextField nowTimeStamp;

    /**
     * 转换按钮 时间戳 --> 时间
     */
    private JButton timestampToTimeTransfer;

    /**
     * 时间转时间戳文本
     */
    private JTextField timestampToTimeText;

    /**
     * 时间戳转时间单位
     */
    private JComboBox timestampToTimeUnit;

    /**
     * 时间戳转时间结果
     */
    private JTextField timestampToTimeResult;

    /**
     * 时间转时间戳文本
     */
    private JTextField timeToTimestampText;

    /**
     * 转换按钮 时间 --> 时间戳
     */
    private JButton timeToTimestampTransfer;
    private JTextField timeToTimestampResult;
    private JComboBox timeToTimestampUnit;

    private Timer AUTO_TIMER;


    /**
     * 默认自动时间戳启动
     */
    private boolean autoTimeSwitch = true;

    static Function<LocalDateTime, Long> TIME_2_TIMESTAMP_MILLI = l -> l.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    static Function<LocalDateTime, Long> TIME_2_TIMESTAMP_SECOND = l -> l.toInstant(ZoneOffset.of("+8")).toEpochMilli() / 1000;
    static Function<Long, LocalDateTime> TIMESTAMP_2_TIME_MILLI = t -> LocalDateTime.ofEpochSecond(t / 1000, 0, ZoneOffset.ofHours(8));
    static Function<Long, LocalDateTime> TIMESTAMP_2_TIME_SECOND = t -> LocalDateTime.ofEpochSecond(t, 0, ZoneOffset.ofHours(8));

    static BiFunction<LocalDateTime, DateTimeFormatter, String> TIME_2_STRING = (l, f) -> f.format(l);

    public TimeStampWindowUi(Project project, Disposable disposable, ToolWindow toolWindow) {
        // 自动定时器
        initAutoTimeStamp();
        // 时间戳 --> 时间
        initTimestampToTime();
        // 时间 --> 时间戳
        initTimeToTimestamp();
    }

    public static LocalDateTime parseTime(String[] timeArrays) {
        LocalDateTime result = null;
        switch (timeArrays.length) {
            case 6:
                result = LocalDateTime.of(
                        Integer.parseInt(timeArrays[0]),
                        Integer.parseInt(timeArrays[1]),
                        Integer.parseInt(timeArrays[2]),
                        Integer.parseInt(timeArrays[3]),
                        Integer.parseInt(timeArrays[4]),
                        Integer.parseInt(timeArrays[5]),
                        0
                );
                break;
            case 2:
                // TODO: 2020/6/4 分年月
            case 1:
                // TODO: 2020/6/4 年月一体
            default:
                break;
        }
        return result;
    }


    private static String[] preTimeStringHandler(String timeString) {
        return timeString.trim().replaceAll("[^\\d]+", " ").split(" ");
    }

    private void initAutoTimeStamp() {
        // 文本不可编辑
        nowTimeStamp.setEditable(false);
        // 定时器启动
        AUTO_TIMER = new Timer(1000, this);
        AUTO_TIMER.start();
        // 定时器控制按键
        control.setIcon(AllIcons.General.InspectionsOK);
        control.addActionListener(e -> {
            autoTimeSwitch = !autoTimeSwitch;
            switchAuto(autoTimeSwitch);
            control.setIcon(autoTimeSwitch ? AllIcons.General.InspectionsOK : AllIcons.General.InspectionsTrafficOff);
        });
    }

    private void initTimestampToTime() {
        timestampToTimeTransfer.addActionListener(e -> {
            String timestampString = timestampToTimeText.getText();
            // 10 or 13 bit timestamp
            LocalDateTime localDateTime = null;
            try {
                long timestamp = Long.parseLong(timestampString);
                int selectedIndex = timestampToTimeUnit.getSelectedIndex();
                TimeUnitEnum timeUnit = TimeUnitEnum.getByCode(selectedIndex);
                switch (timeUnit) {
                    case SECOND:
                        // 秒
                        localDateTime = TIMESTAMP_2_TIME_SECOND.apply(timestamp);
                        break;
                    case MILLISECOND:
                        // 毫秒
                        localDateTime = TIMESTAMP_2_TIME_MILLI.apply(timestamp);
                        break;
                    default:
                        // 啥都不是
                }
                String result = Optional.of(localDateTime).map(l -> TIME_2_STRING.apply(l, ISO_LOCAL_DATE_TIME)).orElse("error");
                timestampToTimeResult.setEditable(false);
                timestampToTimeResult.setText(result);
            } catch (NumberFormatException numberFormatException) {
                // TODO: 2020/6/3 格式不对哦
                timestampToTimeResult.setText("your mother's input is wrong!!");
            }
        });
    }

    private void initTimeToTimestamp() {
        timeToTimestampTransfer.addActionListener(e -> {
            String timeString = timeToTimestampText.getText();
            String[] timeArrays = preTimeStringHandler(timeString);
            LocalDateTime localDateTime = parseTime(timeArrays);
            int selectedIndex = timeToTimestampUnit.getSelectedIndex();
            TimeUnitEnum timeUnit = TimeUnitEnum.getByCode(selectedIndex);
            String timestamp = null;
            switch (timeUnit) {
                case SECOND:
                    // 秒
                    timestamp = String.valueOf(TIME_2_TIMESTAMP_SECOND.apply(localDateTime));
                    break;
                case MILLISECOND:
                    // 毫秒
                    timestamp = String.valueOf(TIME_2_TIMESTAMP_MILLI.apply(localDateTime));
                    break;
                default:
                    // 啥都不是
            }
            timeToTimestampResult.setText(timestamp);
        });
    }


    public void switchAuto(boolean autoReadSwitch) {
        if (autoReadSwitch) {
            AUTO_TIMER.start();
        } else {
            AUTO_TIMER.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        nowTimeStamp.setText(System.currentTimeMillis() + "");
    }

    @Override
    public JPanel getContent() {
        return container;
    }

    enum TimeUnitEnum {

        /**
         *
         */
        MILLISECOND(0, "毫秒"),

        /**
         *
         */
        SECOND(1, "秒"),
        ;

        private Integer code;
        private String desc;

        TimeUnitEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        private static Map<Integer, TimeUnitEnum> ENUM_MAP = Arrays.stream(values())
                .collect(Collectors.toMap(TimeUnitEnum::getCode, Function.identity()));

        public static TimeUnitEnum getByCode(Integer code) {
            return ENUM_MAP.getOrDefault(code, null);
        }
    }
}
