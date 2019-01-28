import { IProductAsset } from 'app/shared/model/product-asset.model';
import { IProductSku } from 'app/shared/model/product-sku.model';

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
    description?: any;
    brand?: string;
    sport?: SportType;
    retailPrice?: number;
    membersFirstPrice?: number;
    membersPrice?: number;
    assets?: IProductAsset[];
    availableSkuses?: IProductSku[];
}

export class Product implements IProduct {
    constructor(
        public id?: number,
        public productId?: string,
        public title?: string,
        public description?: any,
        public brand?: string,
        public sport?: SportType,
        public retailPrice?: number,
        public membersFirstPrice?: number,
        public membersPrice?: number,
        public assets?: IProductAsset[],
        public availableSkuses?: IProductSku[]
    ) {}
}
