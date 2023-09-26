import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UploadVideoResponse} from "./upload-video/UploadVideoResponse";
import {VideoDto} from "./video-dto";

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) {
  }

  uploadVideo(fileEntry: File) {
    const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name)

    return this.httpClient.post<UploadVideoResponse>("http://localhost:8080/api/videos", formData);
  }

  uploadThumbnail(fileEntry: File, videoId: string) {
    const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name)
    formData.append("videoId", videoId);

    return this.httpClient.post("http://localhost:8080/api/videos/thumbnail", formData, {
      responseType: "text"
    });
  }

  getVideo(videoId: string){
    return this.httpClient.get<VideoDto>("http://localhost:8080/api/videos/" + videoId);
  }
}
