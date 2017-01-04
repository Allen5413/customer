


//call map
function callMap(){
	try{
		callApp(110,"");
	}catch(e){}
}
function callMapFun(msg){
	if(msg!=""){
		var a=eval( "(" + msg + ")" );
		var x=toBdX(parseFloat(a["x"]),parseFloat(a["y"]));
		var y=toBdY(parseFloat(a["x"]),parseFloat(a["y"]));
		var address=a["address"];
		try{
			callMapReturn(x,y,address);
		}catch(e){}
	}
}

function callApp(funcId,msg){
	var v=getVersion();
	if(v==1){
		callAndroid(funcId,msg);
	}else if(v==2){
		callIos(funcId,msg);
	}
}

function callAndroid(funcId,msg){
	window.app.callFun(funcId,msg);
}
function readFun(funcId,msg){
	if(funcId==110){
		try{
			callMapFun(msg);
		}catch(e){}
	}
}

function callIos(funcId,msg){
	jsCallIos(funcId,msg);
}
function jsCallIos(funcId,msg){
	if(funcId==110){
		jsCallIos110(msg);
	}
}
function iosCallJs(funcId,msg){
	if(funcId==110){
		try{
			callMapFun(msg);
		}catch(e){}
	}
}
function getVersion(){
	var result=0;
	var u = navigator.userAgent, app = navigator.appVersion;
	if(u.indexOf('Android') > -1 || u.indexOf('Linux') > -1){
		result=1;
	}else if(u.indexOf('iPhone') > -1){
		result=2;
	}else if(u.indexOf('iPad') > -1){
		result=2;
	}
	return result;
}
function toBdX(x,y){  
	var pi=Math.PI;
	var z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi); 
	var theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);  
	var result= z * Math.cos(theta) + 0.0065;  
	return result;  
}  
function toBdY(x,y) {  
	var pi=Math.PI;
	var z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);  
	var theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);  
	var result= z * Math.sin(theta) + 0.006;  
	return result;  
}