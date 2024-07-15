# schedule-app
Project: Xếp lịch công việc tối ưu thỏa mãn ràng buộc

Đàm Minh Hải

1. Giới thiệu bài toán Bài toán RCPSP (Resource-Constrained Project Scheduling Problem) là bài toán giải quyết các vấn đề về lập lịch dự án với tài nguyên giới hạn bởi một số ràng buộc hoặc điều kiện nhất định. MS-RCPSP (Multi Skill-RCPSP) là bài toán mở rộng từ bài toán gốc RCPSP sau khi được bổ sung thêm ràng buộc về yếu tố đa kỹ năng của các tài nguyên. Trong bài toán MS-RCPSP, mỗi tài nguyên sẽ có thể có nhiều kỹ năng khác nhau, mỗi kỹ năng có thể có nhiều bậc (mức) kỹ năng khác, mỗi tác vụ cũng yêu cầu tài nguyên thực hiện cần đáp ứng đúng loại kỹ năng và phải đạt một mức nhất định mới có thể thực hiện được.

Đầu vào:

Tập các tài nguyên, mỗi tài nguyên gồm chi phí và một tập các kỹ năng, mỗi kỹ năng có trọng số điểm riêng
Tập các task, mỗi task gồm thời gian thực hiện, tập các task cần phải thực hiện trước
Đầu ra: Danh sách công việc đã được xếp lịch, thỏa mãn các rang buộc và tối ưu về thời gian, chi phí
