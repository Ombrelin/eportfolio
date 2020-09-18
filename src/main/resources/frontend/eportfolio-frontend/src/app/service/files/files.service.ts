import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {EnvService} from "../env/env.service";

@Injectable({
  providedIn: 'root'
})
export class FilesService {

  constructor(
    private http: HttpClient,
    private env: EnvService
  ) {
  }

  async upload(file: File) {
    const formData = new FormData();
    formData.append('file', file, file.name);
    return await this.http.post(`${this.env.apiUrl}/files`, formData,
      {
        responseType: 'text'
      }
    ).toPromise();
  }

}
