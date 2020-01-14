package com.cms.sys.vo;

import com.cms.sys.domain.Dept;
import com.cms.sys.domain.Loginfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
public class DeptVo extends Dept {
    private static final long serialVersionUID=1L;
   private Integer page=1;
   private Integer limit=10;



}
