package college.performance.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 绩效明细
 * </p>
 *
 * @author xuxianbei
 * @since 2023-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("my_performance_detail")
public class MyPerformanceDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 绩效id
     */
    private Integer myPerformanceId;

    /**
     * 指标名称
     */
    private String targetName;

    /**
     * 衡量指标
     */
    private String metrics;

    /**
     * 权重
     */
    private Integer weight;

    private BigDecimal score;

    private String comment;

}
