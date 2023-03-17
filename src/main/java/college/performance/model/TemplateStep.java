package college.performance.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 模板步骤
 * </p>
 *
 * @author xuxianbei
 * @since 2023-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("template_step")
public class TemplateStep implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 步骤id
     */
    private Integer stepValue;

    /**
     * 步骤
     */
    private String stepName;


}
