//点击删除时出现确定框
$(".delete").click(function() {
			var status = confirm("确定删除么");

			if (!status) {
				return false;
			}

		});