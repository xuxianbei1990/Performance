package college.performance.model.Dto;

import college.performance.model.TemplatePerformanceDetail;
import college.performance.model.TemplatePerformanceMain;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xuxianbei
 * Date: 2023/3/6
 * Time: 18:16
 * Version:V1.0
 */
@Data
public class TemplatePerformanceDto implements Serializable {

    private TemplatePerformanceMain templatePerformanceMain;

    private List<TemplatePerformanceDetail> detailList;
}
