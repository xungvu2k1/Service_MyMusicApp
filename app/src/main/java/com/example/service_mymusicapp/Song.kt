package com.example.service_mymusicapp

import java.io.Serializable

// Do cần gửi nhiều dữ liệu qua Intent như hình ảnh, ca sĩ, tên bài hát, các action như play hoặc pause=> tạo đối tượng để chứa các data này
class Song(title: String, singer: String, image: Int, resource: Int) : Serializable{
    var title : String = title
    var singer : String = singer
    var image : Int = image
    var resource : Int = resource //file mp3

}