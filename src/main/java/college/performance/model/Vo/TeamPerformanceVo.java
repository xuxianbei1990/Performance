package college.performance.model.Vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: xuxianbei
 * Date: 2023/3/6
 * Time: 14:16
 * Version:V1.0
 */
@Data
public class TeamPerformanceVo implements Serializable {

    private String teamMemberName;

    private String department;

    private Integer id;

    /**
     * 年度
     */
    private String year;

    /**
     * 周期
     */
    private String period;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 当前步骤
     */
    private String step;

    /**
     * 绩效状态
     */
    private Integer status;

    /**
     * 分数
     */
    private BigDecimal score;

}
