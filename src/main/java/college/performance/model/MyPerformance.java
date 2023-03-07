package college.performance.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 我的绩效
 * </p>
 *
 * @author xuxianbei
 * @since 2023-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("my_performance")
public class MyPerformance implements Serializable {

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
    private Integer step;

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
