var vaild = new Vaildate();

function Vaildate(){}

/**
 * 验证金额
 * @param money
 */
Vaildate.prototype.vaildMoney = function(money){
    var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
    if(exp.test(money) && money > 0){
        return true;
    }else{
        return false;
    }
}