import { IUser } from 'app/core/user/user.model';
import { ITcaOrder } from 'app/shared/model//tca-order.model';

export interface ITcaMember {
    id?: number;
    nickname?: string;
    user?: IUser;
    orders?: ITcaOrder[];
}

export class TcaMember implements ITcaMember {
    constructor(public id?: number, public nickname?: string, public user?: IUser, public orders?: ITcaOrder[]) {}
}
