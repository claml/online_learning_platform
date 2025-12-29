INSERT INTO users(id, username, password, role) VALUES
 (1, 'admin', '$2a$10$X3jpQ6p6Nn6.vI8t8NU9meDNiHmDzw/Rlt19HolJXii4cU09pIEaa', 'ADMIN'),
 (2, 'instructor', '$2a$10$X3jpQ6p6Nn6.vI8t8NU9meDNiHmDzw/Rlt19HolJXii4cU09pIEaa', 'INSTRUCTOR'),
 (3, 'student', '$2a$10$X3jpQ6p6Nn6.vI8t8NU9meDNiHmDzw/Rlt19HolJXii4cU09pIEaa', 'STUDENT')
ON DUPLICATE KEY UPDATE username=VALUES(username);

INSERT INTO course(id, title, description, language, domain, difficulty, cover_image) VALUES
 (1, 'Java Web 开发', '学习 Java Web 与 Spring Boot', 'Java', 'Web', '中级', 'https://placehold.co/600x400'),
 (2, 'Python 数据分析', '用 Python 进行数据分析入门', 'Python', '数据分析', '初级', 'https://placehold.co/600x400')
ON DUPLICATE KEY UPDATE title=VALUES(title);

INSERT INTO lesson(id, title, content, video_url, lesson_order, example_code, course_id) VALUES
 (1, 'Spring Boot 入门', '快速创建 Spring Boot 项目', 'https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_1mb.mp4', 1, 'System.out.println("Hello Spring Boot");', 1),
 (2, 'REST 接口设计', '设计 RESTful API', 'https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_1mb.mp4', 2, '@GetMapping("/hello")', 1),
 (3, 'Numpy 使用', '数组处理', 'https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_1mb.mp4', 1, 'import numpy as np', 2)
ON DUPLICATE KEY UPDATE title=VALUES(title);

INSERT INTO problem(id, title, description, sample_input, sample_output, tags, course_id) VALUES
 (1, '两数之和', '给定数组，返回两个数字下标', 'nums=[2,7,11,15], target=9', '0 1', '数组,哈希', 1),
 (2, '字符串反转', '反转输入字符串', 'hello', 'olleh', '字符串', 2)
ON DUPLICATE KEY UPDATE title=VALUES(title);
