const BASE_URL = (import.meta.env.VITE_API_URL as string) || 'http://localhost:8080';

const api = {
  get: async (path: string, options?: RequestInit) => {
    const res = await fetch(`${BASE_URL}${path}`, {
      headers: { 'Content-Type': 'application/json' },
      ...options,
    });
    const json = await res.json();
    if (!res.ok) throw new Error(json.message || `Lỗi server: ${res.status}`);
    return json.data;
  },
  post: async (path: string, data: any, options?: RequestInit) => {
    const res = await fetch(`${BASE_URL}${path}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
      ...options,
    });
    const json = await res.json();
    if (!res.ok) throw new Error(json.message || `Lỗi server: ${res.status}`);
    return json.data;
  },
  put: async (path: string, data: any, options?: RequestInit) => {
    const res = await fetch(`${BASE_URL}${path}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
      ...options,
    });
    const json = await res.json();
    if (!res.ok) throw new Error(json.message || `Lỗi server: ${res.status}`);
    return json.data;
  },
  delete: async (path: string, options?: RequestInit) => {
    const res = await fetch(`${BASE_URL}${path}`, {
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json' },
      ...options,
    });
    const json = await res.json();
    if (!res.ok) throw new Error(json.message || `Lỗi server: ${res.status}`);
    return json.data;
  }
};

export default api;
