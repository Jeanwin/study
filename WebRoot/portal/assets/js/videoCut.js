function videoCut(){
	if(videoCut.self){
		var e = new Error();
		e.message = '禁止再实例化';
		throw e;		
		return;	
	}
	videoCut.self = this;
	this.videoList = [];
	this.getById('player').oncontextmenu = function(){return false;};
	this.getById('progressbar').onmousedown = function(e){videoCut.in().onprogressclick(e);}
	this.getById('point').onmousedown = function(e){
		if(!videoCut.in().isready){return;}
		videoCut.in().startDrag(e);
		if (!document.all) e.preventDefault(); 
		document.onmousemove = function(e){videoCut.in().Drag(e)}
		document.onmouseup = function(e){
			document.onmousemove = null;
			document.onmouseup = null;
		}
	}	
}
videoCut.in = function(){
		if(!this.self){
			this.self = new videoCut();
			return this.self;
		}else{
			return this.self;	
		}
	}
videoCut.self = null;
videoCut.prototype = {
	videoList:Array,
	v_times:Array,
	activeI:Number,
	isready:Boolean,
	currentTime:Number,
	Dx:Number,
	Ox:Number,
	startPoint:Object,
	endPoint:Object,
	duration:Number,
	dragObj:Object,
	checking:Boolean,
	callback:Function,
	checkover:Function,
	init:function(list){
		this.checking = false;
		this.callback = null;
		this.getById('progress').style['background-color'] = '#DDD';
		this.clear();
		this.isready = false;
		this.activeI = 0;
		for(var i = 0 ;i<list.length;i++){
			this.videoList.push(this.createVideoDom(i,list[i].fileUrl));
		}
	},
	createVideoDom:function(i,url){
		var m = this.getById('player');
		var m_v = document.createElement("video");
		m_v.style.width = '100%';
		m_v.style.height = '100%';
		m_v.autoplay = 'autoplay';
		m_v.src = url;
//		m_v.target = this;
		if(i!=0){
			m_v.style.display = 'none';
		}
		m.appendChild(m_v);
		this.addEventListenner(m_v)
		return m_v;
	},
	clear:function(){
		this.videoList = [];
		this.getById('player').innerHTML = '';
	},
	seek:function(time){
		for(var i=0;i<this.v_times.length;i++){
			if(time<this.v_times[i]){			
				break;
			}
		}
		if(i!=0){
			time = time - this.v_times[i-1]	
		}
		this.showVideo(i);
		this.videoList[this.activeI].currentTime =time;
		this.ontimeupdate(null);
	},
	startDrag:function(e){
		if(!e.target.id){
			this.dragObj =  e.target.parentElement || e.target.parentNode || null;
			var ml = this.dragObj.style['margin-left']||'0px';
			this.Dx = e.screenX-Number(ml.slice(0,ml.length-2));
			if (!document.all) e.preventDefault(); 
		document.onmousemove = function(e){videoCut.in().DragCutPoint(e)}
		document.onmouseup = function(e){
			document.onmousemove = null;
			document.onmouseup = null;
			videoCut.in().dragObj = null;
		}
			return;
		}
		var ml = this.getById('point').style['margin-left'];
		ml = Number(ml.slice(0,ml.length-2));
		this.Dx = e.screenX-ml;
		this.Ox = e.offsetX;
	},
	Drag:function(e){
		var x = e.screenX-this.Dx;
		x = Math.min(Math.max(x,-7),this.getById('progressbar').clientWidth-7)
		this.getById('point').style['margin-left']=x+'px';
		this.getById('progress').style.width = (x+7)+'px';
		this.seek(Math.floor((x+7)/this.getById('progressbar').clientWidth*this.v_times[this.v_times.length-1]*10)*0.1);
	},
	DragCutPoint:function(e){
		if(this.checking){
			this.checking=false;
			this.pause();
		}
		var dx = Math.min(Math.max(e.screenX - this.Dx,0),this.getById('progressbar').clientWidth);
		var t =Math.floor(dx/this.getById('progressbar').clientWidth*this.v_times[this.v_times.length-1]*10)*0.1;
		if(!(this.dragObj.link==this.startPoint&&t<this.endPoint.t)&&!(this.dragObj.link==this.endPoint&&t>this.startPoint.t)){
			var o=this.startPoint.o,l=this.endPoint.o;
			this.startPoint.o = this.endPoint.o
			this.endPoint.o = o;
			o.link = this.endPoint;
			l.link = this.startPoint;
			if(this.dragObj==o){
				this.setStartPoint(this.endPoint.t);
			}else{
				this.setEndPoint(this.startPoint.t);
			}
		}
		if(this.dragObj.link==this.startPoint){
			this.setStartPoint(t);
		}else{
			this.setEndPoint(t);
		}
		this.dragObj.style['margin-left'] = dx+'px';
	},
	showVideo:function(i){
		if(i==this.activeI){
			return;
		};
		//alert(this.activeI);
		this.videoList[this.activeI].style.display = 'none';
		this.videoList[i].style.display = 'block';
		this.videoList[this.activeI].pause();
		this.activeI = i;
		var starttime=0;
		var endtime= this.duration;
		for(j=0;j<i;j++){
			starttime+=this.videoList[j].duration;
		};
		endtime = starttime+this.videoList[i].duration;
		this.setEndPoint(endtime);
		this.setStartPoint(starttime);
		this.videoList[i].currentTime = 0;
		this.videoList[i].play();
		this.getById("pausebtn").style.display = 'block';
		this.getById("playbtn").style.display = 'none';
	},
	addEventListenner:function(dom){
		dom.addEventListener('loadedmetadata',this.onloadedmetadata);
		dom.addEventListener('timeupdate',this.ontimeupdate);
		dom.addEventListener('ended',this.onended);
	},
	ontimeupdate:function(e){
		var m=videoCut.in();
		m.currentTime=Math.floor(m.videoList[m.activeI].currentTime+(m.activeI>0?m.v_times[m.activeI-1]:0));
		m.getById('progress').style.width = m.currentTime/m.v_times[m.v_times.length-1]*100+'%'
		m.getById('point').style['margin-left']=(m.getById('progress').clientWidth-7)+'px';
		if(m.callback){m.callback(m.currentTime,m.activeI)}
		if(m.checking&&m.activeI==m.endPoint.i&&m.currentTime>m.endPoint.t){
			m.pause();
			m.checking=false;
			if(m.checkover){m.checkover(1)}
		}
	},
	onprogressclick:function(e){
		var m=videoCut.in();
		if(!m.isready||e.target===m.getById('point')||/cutpoint/.test(e.target.id)){
			return;
		}
		if(e.target.className=='upar'){
			this.startDrag(e);
			return;
		}
		var lsblx = 0;
		if(/jj_/.test(e.target.id)){
			var value = e.target.style['margin-left'];
			if(value.indexOf('%')>0){
				lsblx = parseInt(value.replace(/%/,''))/100*m.getById('progressbar').clientWidth;
			}else if(value.indexOf('px')>0){
				lsblx = parseInt(value.replace(/px/,''));
			}
		}
		var t =Math.floor((lsblx+e.offsetX)/m.getById('progressbar').clientWidth*m.v_times[m.v_times.length-1]*10)*0.1;
		this.pause();			
		m.seek(t);
		this.getById('point').onmousedown(e);
		if(m.checking&&t>=m.startPoint.t&&t<m.endPoint.t){
			this.play();
		}else{
			m.checking=false;
			if(m.checkover){m.checkover(0)}
		}
	},
	onloadedmetadata:function(e){
		videoCut.in().checkReady();
	},
	onended:function(e){
		if(videoCut.in().activeI<videoCut.in().videoList.length-1){					
			videoCut.in().showVideo(videoCut.in().activeI+1);
			videoCut.in().videoList[videoCut.in().activeI].currentTime = 0;
			videoCut.in().videoList[videoCut.in().activeI].play();
		}	
	},
	checkReady:function(){
		for(var i = 0 ;i <this.videoList.length;i++){
			if(!this.videoList[i].duration){
				return;
			}
		}
		var vtn = 0;
		this.v_times = [];
		for(var j=0;j<this.videoList.length;j++){
			this.v_times[j] = vtn + this.videoList[j].duration;
			vtn += this.videoList[j].duration;
		}
		this.duration = vtn;
		this.startPoint= {t:0,i:0,ct:0,o:this.getById('cutpoint0')};
		this.getById('cutpoint0').link = this.startPoint;
		this.endPoint={t:vtn,i:j-1,ct:this.videoList[j-1].duration,o:this.getById('cutpoint1')};
		this.getById('cutpoint1').link = this.endPoint;
		this.setStartPoint(0);
		//this.setEndPoint(this.endPoint.ct);
		this.setEndPoint(vtn);
		this.isready = true;
		this.getById('playbtn').onclick = function(e){videoCut.in().play()};
		this.getById('pausebtn').onclick = function(e){videoCut.in().pause()};
		this.getById('setStartPoint').onclick = function(e){videoCut.in().setStartPoint(-1)};
		this.getById('setEndPoint').onclick = function(e){videoCut.in().setEndPoint(-1)};	
		this.getById('checkVideo').onclick = function(e){videoCut.in().checkVideo()};	
			
		this.seek(0);
	},
	play:function(){
		if(!this.isready) return;
		this.videoList[this.activeI].play();
		this.getById('playbtn').style.display = 'none';
		this.getById('pausebtn').style.display = 'inline-block';
	},
	pause:function(){
		if(!this.isready) return;
		this.videoList[this.activeI].pause();
		this.getById('playbtn').style.display = 'inline-block';
		this.getById('pausebtn').style.display = 'none';
	},
	setStartPoint:function(t){
		var time,i,ct;
		if(t==-1){
			time = Math.floor((this.videoList[this.activeI].currentTime + (this.activeI>0?this.v_times[this.activeI-1]:0))*10)/10;
			i = this.activeI;
			ct = time-(this.activeI>0?this.v_times[this.activeI-1]:0);
			if(this.endPoint.t<time){
				this.setEndPoint(time);
			}
		}else{
			time = t;
			for(var j=0;j<this.v_times.length;j++){
				if(time<=this.v_times[j]){			
					break;
				}
			}
			if(j!=0){
				ct = time - this.v_times[j-1]	
			}else{
				ct = time;
			}
			i = j;	
		}		
		this.startPoint.t=time;
		this.startPoint.i=i;
		this.startPoint.ct=ct;
		this.startPoint.o.style['margin-left'] = this.moveUpAr(time)+'px';
		this.showjjbar();
		this.getById('setStartPoint_input').value =this.displayTime(time);
	},
	setEndPoint:function(t){
		var time,i,ct;
		if(t==-1){
			time = Math.floor((this.videoList[this.activeI].currentTime + (this.activeI>0?this.v_times[this.activeI-1]:0))*10)/10;
			i = this.activeI;
			ct = time-(this.activeI>0?this.v_times[this.activeI-1]:0);
			if(this.startPoint.t>time){
				this.setStartPoint(time);
			}
		}else{
			time = t;
			for(var j=0;j<this.v_times.length;j++){
				if(time<=this.v_times[j]){			
					break;
				}
			}
			if(j!=0){
				ct = time - this.v_times[j-1]	
			}else{
				ct = time;
			}
			i = j;
		}
		this.endPoint.t=time;
		this.endPoint.i=i;
		this.endPoint.ct=ct;
		this.endPoint.o.style['margin-left'] = this.moveUpAr(time)+'px';
		this.showjjbar();
		this.getById('setEndPoint_input').value =this.displayTime(time);
	},
	moveUpAr:function(t){
		return t/this.v_times[this.v_times.length-1]*this.getById('progressbar').clientWidth;
	},
	showjjbar:function(){
		this.getById('jj_0').style['margin-left'] = this.startPoint.o.style['margin-left'];
		var m = this.endPoint.o.style['margin-left']||'0px',n =this.startPoint.o.style['margin-left']||'0px';
		m = Number(m.slice(0,m.length-2));
		n = Number(n.slice(0,n.length-2));
		this.getById('jj_0').style.width = (m-n)+'px';
		this.data = [];
		var se=(this.endPoint.i==this.startPoint.i?this.endPoint.t:this.v_times[this.startPoint.i])-(this.startPoint.i!=0?this.v_times[this.startPoint.i-1]:0)
		,es=(this.endPoint.i==this.startPoint.i?this.startPoint.t:this.v_times[this.endPoint.i-1])- (this.endPoint.i!=0?this.v_times[this.endPoint.i-1]:0);
		if(this.startPoint.i!=this.endPoint.i){
			for(var i=this.startPoint.i+1;i<this.endPoint.i-1;i++){
				this.data.push({"i":i,"s":0,"e":(this.v_times[i]-this.v_times[i-1])});
			}
			this.data.unshift({"i":this.startPoint.i,"s":this.startPoint.t-(this.startPoint.i!=0?this.v_times[this.startPoint.i-1]:0),"e":se});
			this.data.push({"i":this.endPoint.i,"s":es,"e":this.endPoint.t-this.v_times[this.endPoint.i-1]});
		}else{
			this.data = [{"i":this.endPoint.i,"s":es,"e":se}];
		}
	},
	checkVideo:function(){
		if(this.startPoint.t<this.endPoint.t){
			this.checking = true;
			this.seek(this.startPoint.t);
			this.play();
		}
	},
	setJJvideo:function(s,e){
		this.setStartPoint(s);
		this.setEndPoint(e);
	},
	displayTime:function(t){
		t = Math.floor(t*10);
		var xs = t%10;
		t = (t-xs)/10;
		return this.timeToString(t)+'.'+xs;
	},
	timeToString:function(nS) {   	  
           var t = new Date(parseInt(nS) * 1000); 
           return (t.getHours()<18?'0':'')+(t.getHours()-8)+':'+(t.getMinutes()>9?t.getMinutes():'0'+t.getMinutes())+':'+(t.getSeconds()>9?t.getSeconds():'0'+t.getSeconds());
              
    },
	getById:function(id){
		return document.getElementById(id);	
	}
}



var allowMouseWheelOnTheVideo;
function mouseWheelOnTheVideo(v,w,h){
	if(w<720){return;}
	if(allowMouseWheelOnTheVideo){
		allowMouseWheelOnTheVideo = false;
		setTimeout(function(){allowMouseWheelOnTheVideo = true},2000);
		if(v>0){
			
		}else{
			
		}
	}	
}