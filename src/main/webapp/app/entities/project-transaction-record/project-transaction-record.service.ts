import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ProjectTransactionRecord } from './project-transaction-record.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProjectTransactionRecordService {

    private resourceUrl = SERVER_API_URL + 'api/project-transaction-records';

    constructor(private http: Http) { }

    create(projectTransactionRecord: ProjectTransactionRecord): Observable<ProjectTransactionRecord> {
        const copy = this.convert(projectTransactionRecord);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(projectTransactionRecord: ProjectTransactionRecord): Observable<ProjectTransactionRecord> {
        const copy = this.convert(projectTransactionRecord);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ProjectTransactionRecord> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to ProjectTransactionRecord.
     */
    private convertItemFromServer(json: any): ProjectTransactionRecord {
        const entity: ProjectTransactionRecord = Object.assign(new ProjectTransactionRecord(), json);
        return entity;
    }

    /**
     * Convert a ProjectTransactionRecord to a JSON which can be sent to the server.
     */
    private convert(projectTransactionRecord: ProjectTransactionRecord): ProjectTransactionRecord {
        const copy: ProjectTransactionRecord = Object.assign({}, projectTransactionRecord);
        return copy;
    }
}
