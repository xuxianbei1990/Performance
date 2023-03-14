package college.performance.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 团队绩效
 * </p>
 *
 * @author xuxianbei
 * @since 2023-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("task_pipeline")
public class TaskPipeline implements Serializable {

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
     * 用户名
     */
    private String userName;

    /**
     * 我的绩效
     */
    private Integer templatePerformanceId;

    /**
     * 模板绩效名字
     */
    private String templatePerformanceName;

    /**
     * 步骤
     */
    private Integer step;


}
