package college.performance.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 模板关联用户
 * </p>
 *
 * @author xuxianbei
 * @since 2023-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("template_performance_user")
public class TemplatePerformanceUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 模板id
     */
    private Integer templatePerformanceId;


}
