package college.performance.model.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class MyPerformanceVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

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

    /**
     * 模板id
     */
    private Integer templatePerformanceId;

}
