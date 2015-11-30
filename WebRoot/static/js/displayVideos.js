function displayVideos(list){
	this.videodomlist = list;
	this.videolist = [];
	for(var i = 0; i<list.length; i++){
		this.videolist.push(list[i].childNodes[1]);			
	}
	this.init();
}
displayVideos.prototype = {
	videodomlist:Array,
	videolist:Array,
	activeNum:Number,
	readyActiveNum:Number,
	waitingcount:Number,
	isChange:Boolean,
	isUserPaused:Boolean,
	init:function(){
		console.log('init complete')
		this.waitingcount = 0;
		this.isUserPaused = false;
		for(var v in this.videolist){
			this.videolist[v].linkTarget = this;
			this.videolist[v].addEventListener('seeked',this.onseeked);
//			this.videolist[v].addEventListener('waiting',this.onwaiting);
//			this.videolist[v].addEventListener('playing',this.onplaying);
			if(this.videolist[v].networkStage==3){
				this.videolist[v].load();
			}
		}
		this.addListener(this.videolist[0]);
	},
	changeMainVideo:function(index){
		if(index != 0 ){
			this.isChange = true;
			this.removeListener(this.videolist[0]);
			this.videodomlist[0].innerHTML="";
			this.videodomlist[index].innerHTML="";
			this.videodomlist[0].appendChild(this.videolist[index])
			this.videodomlist[index].appendChild(this.videolist[0])
			
			this.videolist[0].controls = false;
			this.videolist[0].muted = true;

			this.videolist[index].controls = true;
			this.videolist[index].muted = false;
			
			if(!this.isUserPaused){	
			this.videolist[0].play();
			this.videolist[index].play();
			}
			
			var temp = this.videolist[index]
			this.videolist[index] = this.videolist[0];
			this.videolist[0] = temp
			
			
			
			this.addListener(this.videolist[0]);
		}
	},
	addListener:function(video){
		video.addEventListener('error',this.onVideoError);
		video.addEventListener('canplay',this.oncanplay);
		video.addEventListener('ended',this.onended);
		video.addEventListener('pause',this.onpause);
		video.addEventListener('play',this.onplay);
		video.addEventListener('seeking',this.onseeking);
		video.addEventListener('timeupdate',this.ontimeupdate);
	},
	removeListener:function(video){
		video.removeEventListener('error',this.onVideoError);
		video.removeEventListener('canplay',this.oncanplay);
		video.removeEventListener('ended',this.onended);
		video.removeEventListener('pause',this.onpause);
		video.removeEventListener('play',this.onplay);
		video.removeEventListener('seeking',this.onseeking);
		video.removeEventListener('timeupdate',this.ontimeupdate);
	},
	
	updateActiveNum:function(){
		var x = 0;
		for(var v in this.videolist){
			if(this.videolist[v].networkState==1||this.videolist[v].networkState==2){
				x++;
			}
		}
		this.activeNum = x;
		this.readyActiveNum = 0;
	},
	
	
	onVideoError:function(e){
		console.log(e);
	},
	oncanplay:function(e){
	},
	onended:function(e){
	},
	onpause:function(e){
		console.log('pause',e.target,e.target.linkTarget.isChange)
		if(e.target.linkTarget.isChange){
			e.target.linkTarget.isChange =false;
			return;	
		}
		e.target.linkTarget.isUserPaused = true;
		e = (e.videolist?e.videolist:e.target.linkTarget.videolist);
		for(var v in e){
			try{
				if(!e[v].paused){	
					e[v].pause();
				}
			}catch(er){};
		}
	},
	onplay:function(e){
		e.target.linkTarget.isUserPaused = false;
		e = (e.videolist?e.videolist:e.target.linkTarget.videolist);
		for(var v in e){
			try{
				if(e[v].paused){	
					e[v].play();	
				}
			}catch(er){};
		}
	},
	onseeking:function(e){
		console.log('seeking')
		e = e.target;
		e.linkTarget.updateActiveNum();
		var f = e.linkTarget.videolist;
		for(var v in f){
			if(f[v]!=e){
				try{	f[v].currentTime = e.currentTime;	}catch(er){};	
			}
		}
		console.log(e.currentTime);
	},
	onseeked:function(e){
		e.target.pause();
		e = e.target.linkTarget;
		if(++e.readyActiveNum>=e.activeNum){
			e.videolist[0].play();
		}
	},
	ontimeupdate:function(e){
//		console.log(e);
	},
	onwaiting:function(e){
		console.log('waiting')
		e = e.target.linkTarget;
		e.videolist[0].pause();
		e.waitingcount++;
	},
	onplaying:function(e){
		console.log('playing')
		e = e.target.linkTarget;
		if(--e.waitingcount==0){
			e.videolist[0].play();
		}
	}
}