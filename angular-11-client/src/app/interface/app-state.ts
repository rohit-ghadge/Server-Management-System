import { DataState } from "../enum/data-state.enum";

export interface AppState<T> 
{
    dataState: DataState;  // Mandatory
    appData?: T;           // Optional
    error?: string;        // Optional
}