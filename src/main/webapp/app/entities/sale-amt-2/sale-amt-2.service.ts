import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { SaleAmt2 } from './sale-amt-2.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SaleAmt2Service {

    private resourceUrl = SERVER_API_URL + 'api/sale-amt-2-s';

    constructor(private http: Http) { }

    create(saleAmt2: SaleAmt2): Observable<SaleAmt2> {
        const copy = this.convert(saleAmt2);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(saleAmt2: SaleAmt2): Observable<SaleAmt2> {
        const copy = this.convert(saleAmt2);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<SaleAmt2> {
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
     * Convert a returned JSON object to SaleAmt2.
     */
    private convertItemFromServer(json: any): SaleAmt2 {
        const entity: SaleAmt2 = Object.assign(new SaleAmt2(), json);
        return entity;
    }

    /**
     * Convert a SaleAmt2 to a JSON which can be sent to the server.
     */
    private convert(saleAmt2: SaleAmt2): SaleAmt2 {
        const copy: SaleAmt2 = Object.assign({}, saleAmt2);
        return copy;
    }
}
