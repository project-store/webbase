/**
 * 同一标签支持的最大验证条数
 */
var validateMax = 5;

/**
 * @function 验证方法
 */
var Validator = {

	//通用的正则验证----------------------------------------------------------------------------------------
	Require : /.+/,// 必填项
	Password : /^[A-Za-z\d]{1,16}$/,// 16位字符数组
	Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,// 邮箱格式
	Phone : /^(\d{3}-|\d{4}-)?(\d{8}|\d{7})$/,// 电话格式
	Mobile : /^(13[0-9]|15[0-9]|18[0-9]|14[0-9])\d{8}$/,// 手机格式
	Tel : /^(((\d{3}|\d{4})?(\d{8}|\d{7}))|((\d{3}-|\d{4}-)?(\d{8}|\d{7}))|((13[0-9]|15[0-9]|18[0-9]|14[0-9])\d{8}))$/,// 验证带区号和不带区号的固话、手机号码格式
	Fax : /(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{7,8}$)/,// 传真格式
	PostCode : /^[0-9]{6}$/,// 邮编格式
	Url : /^(http[s]:\/\/)?[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/,//
	Ip4 : /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
	Currency : /^\d+(\.\d+)?$/,// 小数
	English : /^[A-Za-z]+$/,// 英文
	Chinese : /^[\u0391-\uFFE5]+$/,// 中文
	CnAndEng : /^[A-Za-z\u4e00-\u9fa5_]+$/,// 中英文
	CnEngNum : /^[A-Za-z0-9\u4e00-\u9fa5]+$/,// 中文和数字，字母
	Chineseore : /^[A-Za-z0-9\u4e00-\u9fa5_]+$/,// 中文和字母
	eornumber : /^[A-Za-z0-9_]+$/,// 字母和数字
	RoleControle : /^AUTH_[A-Za-z0-9_]+$/,//
	Number : /^[-\+]?\d+$/,// 数字
	PositiveNumber : /^[1-9]\d*$/,// 正整数
	PositiveDouble : /^([1-9]\d*(\.\d+)?|0\.\d*[1-9]\d*)$/,// 正实数
	ZeroPositiveDouble : /^([1-9]\d*(\.\d+)?|0\.\d*[0-9]\d*|0)$/,// 0&正实数
	PosDouble : "this.CheckPosDouble(value, getAttribute('digits' + j + ''))",// 正实数，在验证数组中需要定义位数digits（如：digits:"5-2"代表3位整数，两位小数）
	Integer : /^-?[1-9]\d*$/,// 整数
	Double : /^[-\+]?\d+(\.\d+)?$/,// 实数
	Zip : /^[1-9]\d{5}$/,// 压缩文件
	QQ : /^[1-9]*[1-9][0-9]*$/,// QQ号
	Username : /^[a-z]\w{3,}$/i,// 用户名
	UnSafe : /^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/,// 安全
	Year : /^(\d{4}-|\d{4})$/,
	Path : /^[a-zA-Z]:(((\\(?! )[^/:*?<>\""|\\]+)+\\?)|(\\)?)\s*$/, //文件路径
	IdCard : /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/, // 是否是身份证号
	Date : "this.IsDate(value, getAttribute('min'), getAttribute('format'))",// 是否是日期
	Repeat : "value == document.getElementsByName('userObj.newPassword')[0].value",// 是否重复
	Range : "this.range(value,getAttribute('min'+j+''),getAttribute('max'+j+''))",// 是否在某一范围
	// Range : "getAttribute('min') < (value|0) && (value|0) <
	// getAttribute('max')",// 是否在某一范围
	Compare : "this.compare(value,getAttribute('operator' + j + ''),getAttribute('to' + j + ''),form.id)",// 比较
	CompareLength : "this.compareLength(value,getAttribute('operator' + j + ''),getAttribute('to' + j + ''),form.id)",// 比较长度
	Custom : "this.Exec(value, getAttribute('regexp' + j + ''))",// 正则校验
	Group : "this.MustChecked(getAttribute('name'), getAttribute('min'), getAttribute('max'))",// 组校验
	SafeString : "this.IsSafe(value)",// 是否安全
	Filter : "this.DoFilter(value, getAttribute('accept'))",// 是否是可接受的文件
	CheckLength : "this.checkMaxLen(value,getAttribute('maxLen'+j+''))",//验证输入的长度是否超过最大长度限制
	ErrorItem : [document.forms[0]],// 错误index
	ErrorMessage : ["error:提示！"],// 错误信息

	//zzyh专用的正则验证表达式----------------------------------------------------------------------------------
	cardNo:/^\d{10}$/



}

//注册验证
Ext.apply(Ext.form.field.VTypes, {
    Mobile: function(val, field) {
        return Validator.Mobile.test(val);
    },
    MobileText: '手机格式不正确',
    
    cardNo: function(val, field) {
        return Validator.cardNo.test(val);
    },
    cardNoText: '请输入是10位客户卡号'
});