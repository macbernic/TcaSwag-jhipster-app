import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMemberOrderItem } from 'app/shared/model/member-order-item.model';

type EntityResponseType = HttpResponse<IMemberOrderItem>;
type EntityArrayResponseType = HttpResponse<IMemberOrderItem[]>;

@Injectable({ providedIn: 'root' })
export class MemberOrderItemService {
    public resourceUrl = SERVER_API_URL + 'api/member-order-items';

    constructor(protected http: HttpClient) {}

    create(memberOrderItem: IMemberOrderItem): Observable<EntityResponseType> {
        return this.http.post<IMemberOrderItem>(this.resourceUrl, memberOrderItem, { observe: 'response' });
    }

    update(memberOrderItem: IMemberOrderItem): Observable<EntityResponseType> {
        return this.http.put<IMemberOrderItem>(this.resourceUrl, memberOrderItem, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMemberOrderItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMemberOrderItem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
