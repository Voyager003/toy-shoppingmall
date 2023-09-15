import ItemRequest from "@/services/item/ItemRequest";

export function fetchList(query) {
  return ItemRequest({
    url: '/products',
    method: 'get',
    params: query
  });
}