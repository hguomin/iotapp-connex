export declare interface EdgeDataSource<TConf> {
    name: string,
    type: string,
    tags: string,
    description: string,
    configuration: TConf
};

export interface DataViewModel<TData> {
    isSelected?: boolean,
    name?: string,
    data: TData
};
