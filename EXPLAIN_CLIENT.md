# 📊 Panel de Métricas - Bazar Online

## ¿Qué información mide el sistema?

A continuación se detallan las métricas clave que el sistema calcula automáticamente con cada venta, cada producto cargado y cada interacción de tus clientes.

---

## 1️⃣ Ingresos Totales

**Qué mide:** La suma de todo el dinero facturado en órdenes confirmadas (entregadas, enviadas o en proceso).

**Cómo se calcula:** El sistema suma el `total` de cada orden cuyo estado no sea `cancelada`. Cada orden se compone de la suma de `precio × cantidad` de todos los productos que el cliente compró.

**Ejemplo real (datos de prueba):**
- Cliente compró: Campera ($28,999) + Yoga Mat ($5,499) + 2× Lámpara LED ($13,999) → **$47,497**
- Staff compró: Smartwatch ($15,999) → **$15,999**
- Total acumulado del sistema: **$93,496**

**Para qué sirve:** Saber cuánto dinero está entrando realmente al negocio, sin estimaciones ni cálculos manuales.

---

## 2️⃣ Órdenes Acumuladas

**Qué mide:** La cantidad total de órdenes creadas en la historia del sistema, segmentadas por estado.

**Estados que se tracking:**
| Estado | Significado |
|--------|-------------|
| `PENDING` | Cliente pagó, pendiente de procesar |
| `PROCESSING` | Se está preparando el pedido |
| `SHIPPED` | Pedido enviado al cliente |
| `DELIVERED` | Cliente recibió conforme |
| `CANCELLED` | Pedido cancelado (no suma a ingresos) |

**Para qué sirve:**
- Detectar **cuellos de botella**: muchas `PENDING` y pocas `DELIVERED` indica demora operativa
- Identificar **tasa de conversión**: cuántos pedidos llegan a entregados vs cancelados
- Medir **volumen operativo**: cuántos pedidos procesa el negocio por día/semana/mes

---

## 3️⃣ Ventas por Mes

**Qué mide:** La evolución de ingresos mes a mes, permitiendo ver tendencias, estacionalidad y crecimiento.

**Cómo se calcula:** El sistema agrupa las órdenes por mes de creación y suma los ingresos de cada período.

| Mes | Ingresos |
|-----|----------|
| Enero | $47,497 |
| Febrero | $23,999 |
| Marzo | $5,499 |

**Para qué sirve:**
- **Identificar picos estacionales** (ej: más ventas en diciembre, menos en enero)
- **Medir crecimiento real** mes contra mes
- **Detectar anomalías**: si un mes cae abruptamente sin motivo, algo está pasando
- **Tomar decisiones informadas**: cuándo hacer campañas, cuándo stockear más producto

---

## 4️⃣ Productos por Categoría

**Qué mide:** La distribución del catálogo y las ventas agrupadas por categoría de producto.

**Ejemplo real:**
| Categoría | Cant. Productos | Más Vendido |
|-----------|----------------|-------------|
| Electrónica | 3 | Smartwatch X200 |
| Hogar | 3 | Set Sartenes |
| Moda | 3 | Campera Impermeable |
| Deportes | 3 | Pesas Ajustables |
| Libros | 1 | El Principito |
| Juguetes | 2 | Robot Educativo |

**Para qué sirve:**
- **Identificar categorías fuertes**: si Electrónica vende mucho, conviene ampliar ese catálogo
- **Detectar categorías débiles**: si una categoría tiene muchos productos pero pocas ventas, revisar precios o promociones
- **Planificar compras**: comprar más stock de lo que se vende, menos de lo que no
- **Oportunidad cruzada**: si un cliente compra un producto de Hogar, ofrecerle productos relacionados

---

## 5️⃣ Valor Promedio por Orden (Ticket Promedio)

**Qué mide:** Cuánto gasta en promedio cada cliente por compra.

**Fórmula:** `Ingresos Totales ÷ Cantidad de Órdenes`

**Para qué sirve:**
- Saber si las campañas de descuento realmente aumentan el ticket o lo canibalizan
- Identificar clientes de alto valor (los que compran más por orden)
- Definir estrategias de upselling: "si el ticket promedio es $15,000, ofrecer productos complementarios de $3,000-$5,000"

---

## 6️⃣ Stock Bajo

**Qué mide:** Productos cuyo stock está por debajo de un umbral configurable.

**Para qué sirve:**
- **Evitar perder ventas** por falta de stock
- **Automatizar reposiciones** sabiendo exactamente qué productos se están agotando
- **Identificar productos estrella**: los que más stock rotan

---

## 💰 ¿Cómo esto genera más ganancias de lo que cuesta el sistema?

### Costo del sistema
- Una inversión única de desarrollo + hosting mensual mínimo (VPS ~$10-15 USD/mes, o Cloud)
- Sin costos recurrentes de licencias (código 100% propio)

### Cómo se recupera la inversión:

| Problema sin sistema | Costo oculto | Cómo lo resuelve Bazar Online |
|----------------------|--------------|-------------------------------|
| No sabés qué producto se vende más | Comprás stock de lo que no se vende → dinero muerto en inventario | Reporte de productos por categoría + más vendidos |
| No sabés cuánto vendiste el mes pasado | Decisión a ciegas → campañas inefectivas | Ventas por mes con tendencias claras |
| Perdés pedidos porque "se te pasó" | Clientes insatisfechos → no vuelven | Órdenes con tracking de estado + notificaciones |
| No sabés qué cliente gasta más | No podés fidelizar a tus mejores clientes | Historial de órdenes por usuario |
| Todo en planillas manuales | Horas semanadas perdidas en Excel | Automatización total, datos en tiempo real |

### Escenario concreto:

> **Ejemplo:** Un solo producto con stock bajo no detectado a tiempo puede significar **$30,000 ARS de venta perdida**. Si el sistema te avisa a tiempo y evitás eso aunque sea **una vez al mes**, en 3 meses ya cubriste el costo del desarrollo. Todo lo que sigue es **ganancia pura**.

### Beneficios adicionales que se traducen en plata:

1. **📈 Aumento de ticket promedio** → Ofreciendo productos relacionados cuando un cliente compra
2. **🔄 Fidelización** → Notificaciones automáticas a clientes recurrentes
3. **📉 Reducción de pérdidas** → Sin vencimientos de inventario por compras mal planificadas
4. **⏱️ Ahorro de tiempo** → Lo que antes llevaba 4 horas en Excel ahora está en 1 clic
5. **📊 Decisiones basadas en datos** → dejás de "creer" y empezás a "saber"

---

### Resumen

Este sistema no es un gasto, es una **inversión con retorno medible**. Cada métrica está diseñada para darte una palanca concreta para:
- **Vender más**
- **Gastar menos en inventario improductivo**
- **Atender mejor a tus clientes**
- **Tomar decisiones sin adivinar**

> *"Lo que no se mide, no se puede mejorar. Lo que no se mejora, se degenera."*
