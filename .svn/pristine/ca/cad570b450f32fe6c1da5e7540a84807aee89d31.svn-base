<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="pageInfo" type="com.zonekey.study.entity.Pagebar" required="true" description="翻页对象"%>
<%@ attribute name="formid" type="java.lang.String" description="提交翻页条件的form表单id的名称"%>
	<c:if test="${pageInfo.totalCount>0}"> 
	   <script language="javascript">
			function tp(pagenum,pagesize,frm){
				if(frm==null || frm==''){
					frm=document.getElementById('${formid}');
				}else{
					frm=document.getElementById(frm);
				}
				document.getElementById('page').value=pagenum;
				if (pagesize!=null && pagesize!="")
					document.getElementById('pageSize').value=pagesize;
				frm.submit();
			}
			//判断输入框中的只能是数字，不是数字则置空
			function getPageNum(obj){
				var str = obj.value;
				if(!validateNum(str)){
					obj.value="";
				}
			}
			//判断数字//wuzhonghua
			//由数字组成 true  否则false
			function validateNum(str){
				var patn = /^[0-9-\/]+$/;  //正则表达式，不是数字
				if(patn.test(str)) return true;
				return false;
			}
			//更改每页显示记录数
			function pageNumChange(s){
			    var pagesize =s.value;
			    tp(1,pagesize);
			}
			
			//手工输入页码点击go
			function go(){
			  var pagenum =document.getElementById('tPageNum').value;
			  if (pagenum ==null || pagenum==""){
			     alert("请输入页数.");
			     document.getElementById('tPageNum').focus();
			     return;
			  }
			   tp(pagenum);
			}
		</script>

		<!-- ##google like page number by b.m -->
		<c:choose>
			<c:when test="${pageInfo.page<=6}">    								
			   <c:set value="1" var="startIndex"/>  							
				<c:choose>
					<c:when test="${pageInfo.totalPage>10}"> 						
					  <c:set value="10" var="endIndex"/>  																							
					</c:when>
					<c:otherwise> 																
					  <c:set value="${page.totalPage}" var="endIndex"/>  			
					</c:otherwise>  
				</c:choose>																
			</c:when>      
			<c:otherwise>   
				<c:set value="${pageInfo.page - 6}" var="startIndex"/>       													  
				<c:choose>
					<c:when test="${pageInfo.totalPage > (pageInfo.page + 3)}">   
						 <c:set value="${pageInfo.page + 3}" var="endIndex"/>          
					</c:when>													 
					<c:otherwise>                                                 
						<c:set value="${pageInfo.totalPage}" var="endIndex"/>     
					</c:otherwise>                                                
				</c:choose>
				<c:choose>
					<c:when test="${pageInfo.totalPage > (pageInfo.page + 3)}">
						 <c:set value="${pageInfo.page + 3}" var="endIndex"/>          
					</c:when>													 
					<c:otherwise>
						<c:if test="${pageInfo.totalPage>9 }">
						<c:set value="${pageInfo.totalPage - 9}" var="startIndex"/>                                                  
						</c:if>
						<c:if test="${pageInfo.totalPage<=9 }">
						<c:set value="1" var="startIndex"/>                                                  
						</c:if>
						<c:set value="${pageInfo.totalPage}" var="endIndex"/>     
					</c:otherwise>                                                
				</c:choose>
			</c:otherwise>
		</c:choose>   				
		
		<c:choose>
			<c:when test="${pageInfo.page < pageInfo.totalPage}">       		  
			   <c:set value="${pageInfo.page  + 1}" var="nextIndex"/>  		   
			</c:when>
			<c:otherwise>														
			   <c:set value="1" var="nextIndex"/>  								 
			</c:otherwise>														
		</c:choose>
		<c:set value="${pageInfo.page-1}" var="prevIndex"/>     				
			<%-- <div class="pageTotal">
				共${pageInfo.totalCount}条数据，每页 <select name='tPageSize' id='tPageSize' onChange="pageNumChange(this);"><option selected>15</option><option>10</option><option>20</option></select>条
			</div>
			<span>Page <strong>${pageInfo.page}</strong> of <strong>${pageInfo.totalPage}</strong></span> --%>
			<div class="col-xs-12 text-right margin-top-20" >
				<nav>
					<ul class="pagination">
				<c:if test="${pageInfo.page !=startIndex}">    
					<li><a href="javascript:tp(1);">第一页</a></li>
				</c:if>
				<c:if test="${prevIndex>0}">   										 
					<li><a href="javascript:tp(${prevIndex})"><i class="icon-chevron-left"></i></a></li>
				</c:if>																
				<c:forEach var ="i" begin="${startIndex}" end= "${endIndex}">       
					 <c:choose>
						<c:when test="${i ==pageInfo.page}">                        
						 <li class="active"> <a >${i}</a></li>
						</c:when>
						<c:otherwise>  												
						<li><a href="javascript:tp(${i});">${i}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				
				<c:if test="${nextIndex > 1}">     									
					<li><a href="javascript:tp(${nextIndex});"><i class="icon-chevron-right"></i></a></li>
				</c:if>	
				<c:if test="${pageInfo.page !=endIndex}">    
					<li><a href="javascript:tp(${page.totalPage});">最后一页</a></li>
				</c:if> 
				</ul>
				</nav>
			</div>
	</c:if>		
