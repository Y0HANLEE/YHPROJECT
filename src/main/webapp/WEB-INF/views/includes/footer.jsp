<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>			

			<div id="bottom"></div>			
		 </div>
	    <!-- /#wrapper -->	
	
	    <!-- Bootstrap Core JavaScript -->
	    <script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>	
	    <!-- Metis Menu Plugin JavaScript -->
	    <script src="/resources/vendor/metisMenu/metisMenu.min.js"></script>	
	    <!-- DataTables JavaScript -->
	    <script src="/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
	    <script src="/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
	    <script src="/resources/vendor/datatables-responsive/dataTables.responsive.js"></script>	
	    <!-- Custom Theme JavaScript -->
	    <script src="/resources/dist/js/sb-admin-2.js"></script>
	
		<!-- 반응형웹처리 : 사이드바 토글 -->
		<script>
			$(document).ready(function() {				
				$(".sidebar-nav")
				.attr("class", "sidebar-nav navbar-collapse collapse")
				.attr("aria-expanded", 'false')
				.attr("style", "height:1px");
			});
		</script>		
	</body>
</html>


