window.onload=function(){
    init();
};
function init(){
    var imglist = [
        document.getElementById('banner-image-1'),
        document.getElementById('banner-image-2'),
        document.getElementById('banner-image-3')
    ];
    var divlist = [
        document.getElementById('banner-div1'),
        document.getElementById('banner-div2'),
        document.getElementById('banner-div3')
    ];
    //document.getElementById('hotTwo').style.display = 'none';
    new imgChanging(imglist,divlist,'focus','unfocus');
}
function nexthotCourse(){
    /*document.getElementById('hotTwo').style.display = 'block';
    document.getElementById('hotOne').style.display = 'none';*/
    if($(".topListMain").css("left")>-1354+"px"){
        if(!$(".topListMain").is(":animated")){
            $(".topListMain").stop(true,true).animate({"left":"-=677px"});
        }
    }
}
function prehotCourse(){
    /*document.getElementById('hotTwo').style.display = 'none';
    document.getElementById('hotOne').style.display = 'block';*/
    if($(".topListMain").css("left")<0+"px"){
        if(!$(".topListMain").is(":animated")){
            $(".topListMain").stop(true,true).animate({"left":"+=677px"});
        }
    }
}
function imgChanging(imglist,btnlist,focus,unfocus){
    this.btnlist = btnlist;
    this.imglist =imglist;
    this.focus = focus;
    this.unfocus = unfocus;
    for(var i=0;i<btnlist.length;i++){
        btnlist[i].target = this;
        btnlist[i].onmouseover = function(e){e.target.target.reset(e.target)};
        imglist[i].style.display = i>0?'none':'block';
        btnlist[i].className = i>0?unfocus:focus;
    }
    console.log(this);
    this.start(0);
}
imgChanging.prototype = {
    intervalNum:Number,
    index:Number,
    focus:String,
    unfocus:String,
    imglist:Array,
    btnlist:Array,
    change:function(d){
        this.imglist[this.index].style.display = 'none';
        this.btnlist[this.index].className = this.unfocus;
        this.index = d==-1?(this.index+1)%this.btnlist.length:d;
        this.imglist[this.index].style.display = 'block';
        this.btnlist[this.index].className = this.focus;
    },
    start:function(i){
        this.index = i;
        if(this.intervalNum){
            clearInterval(this.intervalNum);
            this.intervalNum = null;
        }
        this.intervalNum = setInterval(function(e){e.change(-1);},3000,this)
    },
    reset:function(d){
        for(var i = 0 ;i <this.btnlist.length;i++){
            if(d==this.btnlist[i]){
                this.change(i);
                this.start(i);
                break;
            }
        }
    }
};
