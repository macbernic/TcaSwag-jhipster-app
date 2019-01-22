import { Moment } from 'moment';

export const enum SportType {
    RUNNING = 'RUNNING',
    SWIMMING = 'SWIMMING',
    CYCLING = 'CYCLING',
    TRIATHLON = 'TRIATHLON',
    SWIMRUN = 'SWIMRUN'
}

export interface IProduct {
    id?: number;
    productId?: string;
    title?: string;
    descContentType?: string;
    desc?: any;
    creationDate?: Moment;
    brand?: string;
    price?: number;
    subvention?: number;
    sport?: SportType;
}

export class Product implements IProduct {
    constructor(
        public id?: number,
        public productId?: string,
        public title?: string,
        public descContentType?: string,
        public desc?: any,
        public creationDate?: Moment,
        public brand?: string,
        public price?: number,
        public subvention?: number,
        public sport?: SportType
    ) {}
}
