package com.ass.common.utilsExcel;


public class FieldForDTO{
public FieldForDTO(){}

/**
 * 默认类型
 * @param fieldName
 */
public FieldForDTO(String fieldName){
    this.filedName = fieldName;
}


public FieldForDTO(String fieldName,String columnName,Integer index)
{
    this.filedName = fieldName;
    this.columnName = columnName;
    this.index = index;
}

public FieldForDTO(String fieldName,String columnName,String parentIndex,Integer index)
{
    this.filedName = fieldName;
    this.columnName = columnName;
    this.parentIndex = parentIndex;
    this.index = index;
}

public FieldForDTO(String fieldName,String columnName,Integer index,Integer rownum,Integer colnum)
{
    this.filedName = fieldName;
    this.columnName = columnName;
    this.index = index;
    this.rownum = rownum;
    this.colnum = colnum;
}

public FieldForDTO(String fieldName,String columnName,String parentIndex,Integer index,Integer rownum,Integer colnum)
{
    this.filedName = fieldName;
    this.columnName = columnName;
    this.parentIndex = parentIndex;
    this.index = index;
    this.rownum = rownum;
    this.colnum = colnum;
}





/**
 * 字段名称
 */
private String filedName;//map.get(filedName);

private String columnName;//表头文字

private Integer index;

private Integer rownum = 1;//行数

private Integer colnum = 1;//列数

private String parentIndex;


public String getFiledName() {
    return filedName;
}

public void setFiledName(String filedName) {
    this.filedName = filedName;
}




public String getColumnName() {
    return columnName;
}

public void setColumnName(String columnName) {
    this.columnName = columnName;
}

public Integer getIndex() {
    return index;
}

public void setIndex(Integer index) {
    this.index = index;
}


public Integer getRownum() {

    return rownum;
}

public void setRownum(Integer rownum) {

    this.rownum = rownum;
}

public Integer getColnum() {

    return colnum;
}

public void setColnum(Integer colnum) {

    this.colnum = colnum;
}

public String getParentIndex() {

    return parentIndex;
}

public void setParentIndex(String parentIndex) {

    this.parentIndex = parentIndex;
}

}



