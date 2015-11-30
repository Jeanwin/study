<div id="echarts${id}" style="height:350px;width:1500px;border:0px solid #ccc;padding:2px;"></div>
<script src="/study/static/js/echarts/echarts.js"></script>
<script type="text/javascript">
	$(function() {
		require.config({
			paths : {
				echarts : '/study/static/js/echarts/dist'
			}
		});
		document.getElementById('echarts${id}').style.width = document.getElementById('active_chart_bar').style.width;
		require([ 'echarts', 'echarts/chart/bar', ], function(ec) {
			var myChart = ec.init(document.getElementById('echarts${id}'));
			myChart.setOption({
				/* title : {
					//主标题文本，'\n'指定换行  
					text : '${functitle}',
					//主标题文本超链接  
					//副标题文本，'\n'指定换行  
					//副标题文本超链接  
					sublink : 'http://www.stepday.com/myblog/?Echarts',
					//水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）  
					x : 'left',
					//垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）  
					y : 'top'
				}, */
				//提示框，鼠标悬浮交互时的信息提示  
				tooltip : {
					//触发类型，默认（'item'）数据触发，可选为：'item' | 'axis'  
					trigger : 'axis'
				},
				//工具箱，每个图表最多仅有一个工具箱  
				toolbox : {
					//显示策略，可选为：true（显示） | false（隐藏），默认值为false  
					show : true,
					//启用功能，目前支持feature，工具箱自定义功能回调处理  
					feature : {
						//辅助线标志  
						mark : {
							show : false,
						},
						//dataZoom，框选区域缩放，自动与存在的dataZoom控件同步，分别是启用，缩放后退  
						dataZoom : {
							show : false,
							title : {
								dataZoom : '区域缩放',
								dataZoomReset : '区域缩放后退'
							}
						},
						//数据视图，打开数据视图，可设置更多属性,readOnly 默认数据视图为只读(即值为true)，可指定readOnly为false打开编辑功能  
						dataView : {
							show : true,
							readOnly : true
						},
						//magicType，动态类型切换，支持直角系下的折线图、柱状图、堆积、平铺转换  
						magicType : {
							show : false,
							type : [ 'line', 'bar' ]
						},
						//restore，还原，复位原始图表  
						restore : {
							show : true
						},
						//saveAsImage，保存图片（IE8-不支持）,图片类型默认为'png'  
						saveAsImage : {
							show : true
						}
					}
				},
				//是否启用拖拽重计算特性，默认关闭(即值为false)  
				calculable : true,
				//直角坐标系中横轴数组，数组中每一项代表一条横轴坐标轴，仅有一条时可省略数值  
				//横轴通常为类目型，但条形图时则横轴为数值型，散点图时则横纵均为数值型  
				xAxis : [ {
					//显示策略，可选为：true（显示） | false（隐藏），默认值为true  
					show : true,
					//坐标轴类型，横轴默认为类目型'category'  
					type : 'category',
					//类目型坐标轴文本标签数组，指定label内容。 数组项通常为文本，'\n'指定换行  
					data : ${xdata}
				} ],
				//直角坐标系中纵轴数组，数组中每一项代表一条纵轴坐标轴，仅有一条时可省略数值  
				//纵轴通常为数值型，但条形图时则纵轴为类目型  
				yAxis : [ {
					//显示策略，可选为：true（显示） | false（隐藏），默认值为true  
					show : true,
					//坐标轴类型，纵轴默认为数值型'value'  
					type : 'value',
					//分隔区域，默认不显示  
					splitArea : {
						show : true
					}
				} ],

				//sereis的数据: 用于设置图表数据之用。series是一个对象嵌套的结构；对象内包含对象  
				series : [ {
					//系列名称，如果启用legend，该值将被legend.data索引相关  
					name : '报名人数',
					//图表类型，必要参数！如为空或不支持类型，则该系列数据不被显示。  
					type : 'bar',
					//系列中的数据内容数组，折线图以及柱状图时数组长度等于所使用类目轴文本标签数组axis.data的长度，并且他们间是一一对应的。数组项通常为数值  
					data : ${ydata},
					//系列中的数据标注内容  
					barBorderWidth : '30',
					barMaxWidth : '20',
					markPoint : {
						data : [ {
							type : 'max',
							name : '最大值'
						}, {
							type : 'min',
							name : '最小值'
						} ]
					}
				}]
			}).setTheme('macarons');
		})
	});
</script>