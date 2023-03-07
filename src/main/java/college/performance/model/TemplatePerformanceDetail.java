package college.performance.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 模板明细
 * </p>
 *
 * @author xuxianbei
 * @since 2023-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("template_performance_detail")
public class TemplatePerformanceDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 模板id
     */
    private Integer templatePerformanceId;

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


}
